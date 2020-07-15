import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Question} from "../model/question/question";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {
  model:Question = {
    userType: "",
    content:'',
    label:'',
    id:0
  };
  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
  }

  sendQuestionWithOptions(): void{
    let url="http://localhost:8080/questions/add/porters";
    this.httpClient.post(url, this.model).subscribe(res =>
      {
        location.reload();
      },
      error => {
        alert("Error");
      }
    )
  }

}
