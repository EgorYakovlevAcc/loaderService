import {Component, OnInit} from '@angular/core';
import {UserServiceService} from "../service/user-service.service";
import {Customer} from "../customer";

@Component({
  selector: 'app-user-customers',
  templateUrl: './user-customers.component.html',
  styleUrls: ['./user-customers.component.css']
})
export class UserCustomersComponent implements OnInit {
  customers: Customer[];

  constructor(private userService: UserServiceService) {
  }

  ngOnInit(): void {
    this.userService.getAllCustomers().subscribe((result: Customer[]) => {
        this.customers = result;
      }
    )
  }

  deleteCustomer(id) {
    this.userService.deleteUser(id).subscribe(result => {
      location.reload();
    }, error => {
      alert("Error");
    });
  }
}
