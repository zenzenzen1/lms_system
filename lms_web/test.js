const pr = new Promise((resolve, reject) => {
    resolve([1, 2, 3]);
})

console.log(pr);

(() => {
    // console.log(pr);
    pr.then(a => console.log(a))
})();