import {Option} from "../option/option";
import {ImageFile} from "../image-file";

export class Question {
  id:number;
  content:string;
  label:string;
  attachment?:ImageFile;
  userType:string;
}
