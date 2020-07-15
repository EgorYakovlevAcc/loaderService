import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(private httpClient: HttpClient) { }

  getAllPorters(): Observable<any> {
    return this.httpClient.get("all/porters");
  }

  getAllCustomers(): Observable<any> {
    return this.httpClient.get("all/customers");
  }

  deleteCustomer(id): Observable<any> {
    let url = "remove?id=" + id + "c/ustomer";
    return this.httpClient.get(url);
  }

  deletePorter(id): Observable<any> {
    let url = "remove?id=" + id + "/porter";
    return this.httpClient.get(url);
  }
}
