import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, tap, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    //private apiUrl = 'http://localhost:8080/lab4/api/auth'; // - localhost
    private apiUrl = '/lab4/api/auth'; // - helios


    constructor(private http: HttpClient) {
    }

    login(credentials: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/login`, credentials, {responseType: 'text'}).pipe(
            tap(token => localStorage.setItem('token', token)),
            catchError(this.handleError)
        );
    }


    register(credentials: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/register`, credentials).pipe(
            catchError(this.handleError)
        );
    }

    isLoggedIn(): boolean {
        return !!localStorage.getItem('user');
    }

    getCurrentUser(): string {
        return localStorage.getItem('user') || '';
    }

    logout(): void {
        localStorage.removeItem('user');
        localStorage.removeItem('token');
    }

    private handleError(error: any) {
        return throwError(() => new Error(error.message || 'Server error'));
    }
}
