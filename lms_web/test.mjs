let a = [[1, 2], [3, 4]]

let b = a.reduce((prev, curr) => {
    // console.log({prev, curr});
    return [... prev, curr.map(t => t + 10)];
}, [])

console.log(b);