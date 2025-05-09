document.addEventListener('click', function (e) {
    const dd = document.querySelector('.user-dropdown');
    if (dd.contains(e.target)) {
        dd.classList.toggle('open');
    } else {
        dd.classList.remove('open');
    }
});