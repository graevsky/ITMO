import {Component, HostListener, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Router, RouterLink} from "@angular/router";
import {ClockComponent} from "../clock/clock.component";
import {UtilsComponent} from "../utils/utils.component";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../auth.service";
import {tap, throwError} from "rxjs";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-start-page',
  standalone: true,
  imports: [CommonModule, RouterLink, ClockComponent, FormsModule],
  templateUrl: './start-page.component.html',
  styleUrl: './start-page.component.css'
})
export class StartPageComponent implements OnInit {
  isLoggedIn = false;
  currentUser: string = '';
  loginData = {login: '', password: ''};
  registerData = {login: '', password: ''};
  showLogin = false;
  showRegister = false;

  constructor(private utils: UtilsComponent, private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
    this.utils.updateDisplayMode()
    this.isLoggedIn = this.authService.isLoggedIn();
    this.currentUser = this.authService.getCurrentUser();
  }

  showLoginModal() {
    this.showLogin = true;
  }

  closeLoginModal() {
    this.showLogin = false;
  }

    login() {
        this.authService.login(this.loginData).pipe(
            tap(token => {
                localStorage.setItem('user', this.loginData.login);
                localStorage.setItem('token', token);
                this.isLoggedIn = true;
                this.currentUser = this.loginData.login;
                this.closeLoginModal();
                this.router.navigate(['/main-page']);
            }),
            catchError(error => {
                alert('Ошибка входа. Проверь логин или пароль');
                return throwError(() => new Error(error.message || 'Server error'));
            })
        ).subscribe();
    }


  showRegisterModal() {
    this.showRegister = true;
  }

  closeRegisterModal() {
    this.showRegister = false;
  }

    register() {
        this.authService.register(this.registerData).pipe(
            tap(() => {
                alert('Регистрация успешна');
                this.closeRegisterModal();
            }),
            catchError(error => {
                alert('Ошибка регистрации. Возможно, логин занят');
                console.error('Ошибка регистрации', error);
                return throwError(() => new Error(error.message || 'Server error'));
            })
        ).subscribe();
    }


  @HostListener('window:resize', ['$event'])
  onResize() {
    this.utils.updateDisplayMode();
  }

}
