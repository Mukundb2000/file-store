import Uploader from "./upload.js";
import List from "./list.js";
import Delete from "./delete.js";
import WordCount from "./wordcount.js";
import WordFrequency from "./wordfreq.js";
switch (process.argv[2]) {
  case "add":
    const files = process.argv.slice(3, process.argv.length);
    files.forEach(async (file) => await Uploader(file));
    break;
  case "ls":
    List();
    break;
  case "wc":
    WordCount();
    break;
  case "rm":
    Delete(process.argv[3]);
    break;
  case "update":
    Uploader(process.argv[3]);
    break;
  case "freq-words": {
    const args = process.argv.slice(3, process.argv.length);
    let limit = 10;
    let order = "dsc";
    for (let i = 0; i < args.length; i++) {
      if (args[i] == "-n") {
        limit = parseInt(args[i + 1]);
        i++;
        continue;
      } else if (args[i].includes("--order")) {
        order = args[i].split("=")[1];
        continue;
      } else if (args[i].includes("--limit")) {
        limit = args[i].split("=")[1];
        continue;
      }
    }
    if (order != "asc" && order != "dsc") {
      console.log("Invalid order value");
    } else if (limit < 1) {
      console.log("Invalid limit value");
    } else {
      WordFrequency(limit, order);
    }
  }
}
