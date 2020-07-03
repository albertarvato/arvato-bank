import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { HttpClient } from '@angular/common/http';

import { Transaction } from '@shared/models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {
  constructor(private http: HttpClient) {}


  getTransactions(id: number): Observable<Transaction[]> {
    return this.http
      .get<any>(`/transaction/${id}`);
  }


  addTransaction(transaction: Transaction): Observable<Transaction> {
    return this.http
      .post<any>(`/transaction`, transaction);
  }

}