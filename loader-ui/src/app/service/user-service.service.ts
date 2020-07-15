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

  deleteUser(id): Observable<any> {
    let url = "remove?id=" + id;
    return this.httpClient.get(url);
  }
}
