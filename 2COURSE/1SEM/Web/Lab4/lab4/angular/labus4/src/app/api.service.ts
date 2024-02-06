import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Router} from "@angular/router";
import {AuthService} from "./auth.service";


@Injectable({
    providedIn: 'root'
})
export class ApiService {
    //private apiUrl = 'http://localhost:8080/lab4/api/points'; // - localhost
    private apiUrl = '/lab4/api/points'; // - helios


    constructor(private http: HttpClient, private router: Router, private authService: AuthService) {
    }

    public getResults(): Observable<any[]> {
        const headers = new HttpHeaders({
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        });

        return this.http.get<any[]>(this.apiUrl, {headers: headers}).pipe(
            catchError(error => this.handleError(error))
        );
    }

    public sendPoint(pointData: any): Observable<any> {
        const headers = new HttpHeaders({
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        });

        return this.http.post(this.apiUrl, pointData, {headers: headers}).pipe(
            catchError(error => this.handleError(error))
        );
    }

    private handleError(error: HttpErrorResponse) {
        if (error.status === 401) {
            alert("Токен устарел")
            this.authService.logout();
            this.router.navigate(['/']);
        }
        return throwError(() => new Error(error.message || 'Server error'));
    }
}

