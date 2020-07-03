import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

import { Credentials, CredentialsService } from './credentials.service';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

export interface LoginContext {
  username: string;
  password: string;
  remember?: boolean;
}

/**
 * Provides a base for authentication workflow.
 * The login/logout methods should be replaced with proper implementation.
 */
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private credentialsService: CredentialsService, private http: HttpClient) {}

  heartbeat() {
    return this.http.post('/heartbeat', {}).subscribe();
  }

  /**
   * Authenticates the user.
   * @param {LoginContext} context The login parameters.
   * @return {Observable<Credentials>} The user credentials.
   */
  login(context: LoginContext): Observable<Credentials> {
    return this.http
      .post<any>('/login', { username: context.username, password: context.password })
      .pipe(
        map(data => {
          // login successful if there's a jwt token in the response
          if (data && data.token) {
            // store user details and jwt token in local storage to keep user logged in between page refreshes
            const storedata = {
              username: context.username,
              token: data.token
            };
            this.credentialsService.setCredentials(storedata, context.remember);
            return storedata;
          }
        })
      );
  }

  /**
   * Logs out the user and clear credentials.
   * @return True if the user was logged out successfully.
   */
  logout(): Observable<boolean> {
    // Customize credentials invalidation here
    this.credentialsService.setCredentials();
    return of(true);
  }
}