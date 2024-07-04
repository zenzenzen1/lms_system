package filter;

import com.mysql.cj.util.StringUtils;
import context.LmsContextHolder;
import dao.UsersDAO;
import dto.UserPayload;
import dto.UserPayload.SimpleGrantedAuthority;
import dto.response.UrlResponse;
import entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.BeanFactory;
import utils.JwtUtils;
import utils.RedisUtil;
import utils.SlugUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.LmsConstants.Reid.NAMESPACE_ACCOUNT;
import static utils.RedisUtil.generateKeyForRedis;

public class SecurityFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    private UsersDAO userDao;
    private RedisUtil redisUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = BeanFactory.getUserDao();
        redisUtil = BeanFactory.getRedisUtil();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        try {
            if (isPublicResource(request)) {
                Cookie jwtCookie = getJwtCookie(request);
                if ((request.getRequestURI().equals("/home") || request.getRequestURI().equals("/")) && jwtCookie != null) {
                    UserPayload userDetails = validateAndRenewUserSession(jwtCookie.getValue());
                    if (userDetails != null) {
                        initializeUserContext(userDetails, request.getRequestURI());
                        request.setAttribute("authorities", userDetails.getAuthorities().stream()
                                .map(item -> new UrlResponse(item.getAuthority(), SlugUtils.parseString(item.getAuthority()))).toArray());
                    }
                }
                forwardRequest(request, response, request.getRequestURI());
                return;
            }

            Cookie jwtCookie = getJwtCookie(request);
            if (jwtCookie == null) {
                redirectToLoginPage(response);
                return;
            }

            UserPayload userDetails = validateAndRenewUserSession(jwtCookie.getValue());

            if (userDetails == null || !isUriAllowed(userDetails, request.getRequestURI())) {
                redirectToLoginPage(response);
                return;
            }

            initializeUserContext(userDetails, request.getRequestURI());

            request.setAttribute("authorities", userDetails.getAuthorities().stream()
                    .map(item -> new UrlResponse(item.getAuthority(), SlugUtils.parseString(item.getAuthority()))).toArray());
            forwardRequest(request, response, request.getRequestURI());

        } catch (IOException | ServletException ignored) {
            redirectToLoginPage(response);
        } finally {
            LmsContextHolder.removeContext();
        }
    }

    @Override
    public void destroy() {
    }

    private void initializeUserContext(UserPayload userDetails, String requestUri) {
        Users user = userDao.findByUsername(userDetails.getUsername());
        if (user != null) {
            LmsContextHolder.getContext().setUserId(user.getId());
            LmsContextHolder.getContext().setUserName(user.getUsername());
            LmsContextHolder.getContext().setRequestURI(requestUri);
        }
    }

    private boolean isPublicResource(HttpServletRequest request) {
        String path = request.getRequestURI();
        Pattern pattern = Pattern.compile("^/(login|home|register|logout|resource/.*|public/.*)*$");
        Matcher matcher = pattern.matcher(path);
        return matcher.find();
    }

    private Cookie getJwtCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    return cookie;
                }
            }
        }

        return null;
    }

    private UserPayload validateAndRenewUserSession(String jwtToken) throws IOException {
        if(jwtToken == null) return null;
        UserPayload userDetails = JwtUtils.validateToken(jwtToken);

        try {
            String keySessionId = generateKeyForRedis(userDetails.getSessionId());
            Long ttl = redisUtil.getExpire(keySessionId);

            if (ttl == -2L && Boolean.TRUE.equals(userDetails.isRememberMe())) {
                String keySessionValue = NAMESPACE_ACCOUNT + "." + jwtToken;
                String sessionValue = String.valueOf(redisUtil.getValue(keySessionValue));

                if (StringUtils.isNullOrEmpty(sessionValue)) {
                    log.error("Session Expired");
                    return null;
                }
            }

            redisUtil.setExpire(keySessionId, 3 * 60 * 60);

        } catch (Exception ignored) {
        }

        return userDetails;
    }

    private boolean isUriAllowed(UserPayload userDetails, String uri) {
        List<SimpleGrantedAuthority> authorities = userDetails.getAuthorities();

        return authorities.stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .anyMatch(uri::startsWith);
    }

    private void redirectToLoginPage(HttpServletResponse response) throws IOException {
        String htmlResponse = "<script type=\"text/javascript\">"
                + "window.location.href = \"/login\";"
                + "</script>";
        response.setContentType("text/html");
        response.getWriter().write(htmlResponse);
    }

    private void forwardRequest(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }
}
