import {Component, OnInit} from '@angular/core';
import {Question} from "../model/question/question";
import {QuestionServiceService} from "../service/question-service.service";
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ModalAddQuestionComponent} from "../modal-add-question/modal-add-question.component";
import {ImageFileService} from "../service/image-file.service";

@Component({
  selector: 'app-questions',
  templateUrl: './porter-questions.component.html',
  styleUrls: ['./porter-questions.component.css']
})
export class PorterQuestionsComponent implements OnInit {
  questions: Question[];
  public selectedFile;

  constructor(private imageFileService: ImageFileService, private questionService: QuestionServiceService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.questionService.getAllQuestionsPorter().subscribe((result: Question[]) => {
        this.questions = result;
      }
    );
  }

  openCreationNewQuestionFormPorter() {
    let modalForm = this.modalService.open(ModalAddQuestionComponent);
    modalForm.componentInstance.userType = "PORTER";
  }

  deleteQuestion(id) {
    this.questionService.deleteQuestion(id).subscribe(result => {
      location.reload();
    }, error => {
      alert("Error");
    });
  }

  editQuestion(q: Question) {
    let modalForm = this.modalService.open(ModalAddQuestionComponent);
    modalForm.componentInstance.editQuestion = q;
  }

  processFile(questionId: number) {
    this.imageFileService.uploadImage(questionId, this.selectedFile).subscribe(result => {
        alert("Success");
      },
      error => {
        alert("Something go wrong!..")
      });
  }

  onFileChanged(event) {
    this.selectedFile = event.target.files[0];
  }
}
