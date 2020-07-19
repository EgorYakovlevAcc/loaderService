import {Component, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {User} from "../model/user/user";
import {UserServiceService} from "../service/user-service.service";

@Component({
  selector: 'app-modal-add-admin',
  templateUrl: './modal-add-admin.component.html',
  styleUrls: ['./modal-add-admin.component.css']
})
export class ModalAddAdminComponent implements OnInit {
  modalForm: NgbActiveModal;
  admin: User = {
    activeNow: false, color: "", firstName: "", id: 0, lastName: "", presentGiven: false, score: 0, status: false,
    telegramId: '',
    username: ''

  };


  constructor(private userService: UserServiceService, private modalService: NgbModal, private modal: NgbActiveModal) {
    this.modalForm = modal;
  }


  ngOnInit(): void {
  }

  saveAdmin() {
    let i:number = 0;
    this.userService.createAdmin(this.admin).subscribe(result => {
        location.reload();
      },
      error => {
        alert("Error")
      });
    this.modalForm.close("Efrfrf");
  }
}
