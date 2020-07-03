import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { HttpClient } from '@angular/common/http';
import { Balance } from '@shared/models/balance.model';
import { Account } from '@shared/models/account.model';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {
  constructor(private http: HttpClient) {}


  getBalance(id: number): Observable<Balance> {
    return this.http
      .get<any>(`/user/${id}/balance`);
  }

}