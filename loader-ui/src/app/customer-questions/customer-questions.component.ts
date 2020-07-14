import {Component, OnInit} from '@angular/core';
import {Question} from "../model/question/question";
import {ImageFileService} from "../service/image-file.service";
import {QuestionServiceService} from "../service/question-service.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ModalAddQuestionComponent} from "../modal-add-question/modal-add-question.component";

@Component({
  selector: 'app-customer-questions',
  templateUrl: './customer-questions.component.html',
  styleUrls: ['./customer-questions.component.css']
})
export class CustomerQuestionsComponent implements OnInit {
  questions: Question[];
  public selectedFile;

  constructor(private imageFileService: ImageFileService, private questionService: QuestionServiceService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.questionService.getAllQuestionsCustomer().subscribe((result: Question[]) => {
        this.questions = result;
      }
    );
  }

  openCreationNewQuestionFormCustomer() {
    this.modalService.open(ModalAddQuestionComponent);
  }

  deleteQuestion(id) {
    this.questionService.deleteQuestion(id).subscribe(result => {
      location.reload();
    }, error => {
      alert("Error");
    });

  }
}
