console.log("Hello World!");
age = 21;
name = "someone you don't know";
place = "a place you've never been";

storingNullValue = null;
storingUndefinedValue = undefined;
console.log(storingNullValue);
console.log(storingUndefinedValue);
console.log("My name is " + name + ", I'm " + age + " years old and I live in " + place + ".");

let isRaining = false;
let anyVariable;
anyVariable = 23;
console.log(anyVariable);
anyVariable = "Now I am a string";
console.log(anyVariable);
anyVariable = true;
console.log(anyVariable);
anyVariable = null;
console.log(anyVariable);
anyVariable = undefined;
console.log(anyVariable);

const birthYear = 2003;
console.log("I was born in " + birthYear);
//birthYear = 1990;
console.log(birthYear);

let undefinedVariable;
let nullVariable=null;
let bigIntVariable = BigInt(123);
let symbolVariable = Symbol("this is a symbol!");

//creating an object

const oldAgePerson = {
    name : "Jhangir",
    age : 1009,
    territory : "globe",
    isAlive : false,
};

console.log(oldAgePerson);
console.log(typeof oldAgePerson);
console.log(oldAgePerson.name);
console.log(oldAgePerson.age);
console.log(oldAgePerson.territory);
console.log(oldAgePerson.isAlive);


const product= {
    productName : "Ball pen",
    productPrice :  270,
    discount : 5,
    ratings : 4,
};

console.log(product);
console.log(typeof product);