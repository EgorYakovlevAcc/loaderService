import { Component, OnInit } from '@angular/core';
import {User} from "../model/user/user";
import {UserServiceService} from "../service/user-service.service";

@Component({
  selector: 'app-user-customers',
  templateUrl: './user-customers.component.html',
  styleUrls: ['./user-customers.component.css']
})
export class UserCustomersComponent implements OnInit {
  users: User[];

  constructor(private userService: UserServiceService) {
  }

  ngOnInit(): void {
    this.userService.getAllCustomers().subscribe((result: User[]) => {
        this.users = result;
      }
    )
  }
}
