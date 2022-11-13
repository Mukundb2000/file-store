import fetch from "node-fetch";
const WordCount = async () => {
  var res = await fetch("http://localhost:8081/words/count", {
    method: "GET",
  });
  var response = await res.json();
  console.log("There are " + response.count + " words from all files");
};
export default WordCount;
