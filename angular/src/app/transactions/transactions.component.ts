import { Transaction } from './../@shared/models/transaction.model';
import { Component, OnInit } from '@angular/core';
import { TransactionsService } from '@app/@shared/services/transactions.service';
import { of } from 'rxjs';
import { FormControl } from '@angular/forms';
import { tap, catchError } from 'rxjs/operators';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.scss']
})
export class TransactionsComponent implements OnInit {

  transactions: Transaction[];

  targetUser = new FormControl('');
  amount = new FormControl('');

  constructor(private _transactionsService: TransactionsService) { }

  ngOnInit(): void {
    this._transactionsService.getTransactions(1)
    .pipe(tap(data => this.transactions = data )).subscribe();
  }

  sendMoney(): void {
    console.log(`sending to ${this.targetUser.value}`);
    const transaction: Transaction = {
      accoount_from: 1,
      value: this.amount.value,
      accoount_to: this.targetUser.value,
    };
    this._transactionsService.addTransaction(transaction).pipe(tap((response) => {
      this.transactions.push(response);
      this.resetInputs();
    }),
    catchError(err => {
      this.resetInputs();
      return of(err);
    })).subscribe();
  }

  resetInputs(): void {
    this.amount.setValue(null);
    this.targetUser.setValue(null);
  }

}
