import Uploader from "./upload.js";
import List from "./list.js";
import Delete from "./delete.js";
switch (process.argv[2]) {
  case "add":
    const files = process.argv.slice(3, process.argv.length);
    files.forEach(async (file) => await Uploader(file));
    break;
  case "ls":
    List();
    break;
  case "wc":
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
    let order = "des";
  }
}
