import fetch from "node-fetch";
const WordFrequency = async (limit, order) => {
  var res = await fetch(
    "http://localhost:8081/words/frequency?limit=" + limit + "&order=" + order,
    {
      method: "GET",
    }
  );
  var response = await res.json();
  if (response.length > 0) console.table(response);
  else console.log("There are no words as of now");
};
export default WordFrequency;
