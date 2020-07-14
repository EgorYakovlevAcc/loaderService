import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Question} from "../model/question/question";

@Component({
  selector: 'app-customer-questions',
  templateUrl: './customer-questions.component.html',
  styleUrls: ['./customer-questions.component.css']
})
export class CustomerQuestionsComponent implements OnInit {
  model:Question = {
    userType: "CUSTOMER",
    id:0,
    content:''
  };
  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
  }

  sendQuestionWithOptions(): void{
    let url="http://localhost:8080/questions/add/customers";
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

