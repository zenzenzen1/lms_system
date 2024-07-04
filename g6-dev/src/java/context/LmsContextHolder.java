package context;

public class LmsContextHolder {

    private static final ThreadLocal<LmsContext> contextThreadLocal = new ThreadLocal<>();

    public static LmsContext getContext() {
        LmsContext lmsContext = contextThreadLocal.get();
        if (lmsContext == null) {
            contextThreadLocal.set(new LmsContext());
        }
        return contextThreadLocal.get();
    }

    public static void setContext(LmsContext lmsContext) {
        if (lmsContext == null) {
            throw new IllegalStateException("Error trying to set null to LmsContext");
        }
        contextThreadLocal.set(lmsContext);
    }

    public static void removeContext() {
        contextThreadLocal.remove();
    }

}
