import {Component, OnInit} from '@angular/core';
import {UserServiceService} from "../service/user-service.service";
import {User} from "../model/user/user";
import {Porter} from "../porter";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  porters: Porter[];

  constructor(private userService: UserServiceService) {
  }

  ngOnInit(): void {
    this.userService.getAllPorters().subscribe((result: Porter[]) => {
        this.porters = result;
      }
    )
  }

  deletePorter(id) {
    this.userService.deletePorter(id).subscribe(result => {
      location.reload();
    }, error => {
      alert("Error");
    });
}
}
