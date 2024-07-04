const cookieExists = name => {
    return typeof Cookies.get(name) !== 'undefined';
};

document.addEventListener('DOMContentLoaded', function () {
    var activeSortLink = document.querySelector('.sort.active');
    if (activeSortLink) {
        activeSortLink.setAttribute('data-direction', '${sortDirection}');
    }
});

toastr.options = {
    "positionClass": "toast-bottom-right"
}

function submitForm(element) {
    var currentDirection = element.getAttribute('data-direction');
    var newDirection = (currentDirection === 'asc') ? 'desc' : 'asc';
    element.setAttribute('data-direction', newDirection);

    var up = element.querySelector('.up');
    var down = element.querySelector('.down');
    if (newDirection === 'asc') {
        up.style.display = 'block';
        down.style.display = 'none';
    } else {
        down.style.display = 'block';
        up.style.display = 'none';
    }

    document.getElementById('sort').value = element.getAttribute('data-sort');
    document.getElementById('direction').value = element.getAttribute('data-direction');
    document.getElementById('sortForm').submit();
}

const toggleDisplay = (id, displayState) => {
    document.getElementById(id).style.display = displayState;
};

const toggleLoginLogoutLinks = () => {
    let hasJwt = cookieExists('jwt');

    if (hasJwt) {
        toggleDisplay("login-link", "none");
        toggleDisplay("logout-link", "block");
    } else {
        toggleDisplay("login-link", "block");
        toggleDisplay("logout-link", "none");
    }
};

$(document).ready(function () {
    toggleLoginLogoutLinks();
});