import { RxStompService } from '@stomp/ng2-stompjs';
import { CredentialsService } from '@app/auth';
import { Balance } from './../@shared/models/balance.model';
import { Observable } from 'rxjs';
import { AccountsService } from './../@shared/services/accounts.service';
import { Component, OnInit } from '@angular/core';
import { tap } from 'rxjs/operators';
import { Message } from '@stomp/stompjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  balance$: Observable<Balance>;

  constructor(private accounts: AccountsService, private rxStompService: RxStompService, private credentialsService: CredentialsService) { }

  ngOnInit(): void {
    this.balance$ = this.accounts.getBalance(1);


    this.rxStompService.watch('/topic/balance/1').pipe(
      tap((message: Message) => {
        console.log('got');
        console.log(message.body);
      })).subscribe();

  }

}
