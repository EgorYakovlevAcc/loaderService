import {Option} from "../option/option";
import {ImageFile} from "../image-file";

export class Question {
  id:number;
  content:string;
  attachment?:ImageFile;
  userType:string;
}
