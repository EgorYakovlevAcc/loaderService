import {Component, OnInit} from '@angular/core';
import {User} from "../model/user/user";
import {ModalAddQuestionComponent} from "../modal-add-question/modal-add-question.component";
import {UserServiceService} from "../service/user-service.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {
  admins: User[];

  constructor(private userService: UserServiceService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.userService.getAllAdmins().subscribe((result: User[]) => {
        this.admins = result;
      }
    );
  }

  openCreationNewAdminForm() {
    let modalForm = this.modalService.open(ModalAddQuestionComponent);
    modalForm.componentInstance.userType = "ADMIN";
  }

  deleteAdmin(id) {
    this.userService.deleteAdmin(id).subscribe(result => {
      location.reload();
    }, error => {
      alert("Error");
    });

  }
}
