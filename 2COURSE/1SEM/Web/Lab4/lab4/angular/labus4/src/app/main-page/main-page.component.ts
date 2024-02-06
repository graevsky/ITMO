import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {GraphComponent} from "../graph/graph.component";
import {FormsModule} from "@angular/forms";
import {ApiService} from "../api.service";
import {HttpClientModule} from "@angular/common/http";
import {UtilsComponent} from "../utils/utils.component";
import {AuthService} from "../auth.service";
import {tap, throwError} from "rxjs";
import {catchError} from "rxjs/operators";

@Component({
    selector: 'app-main-page',
    standalone: true,
    imports: [CommonModule, RouterLinkActive, RouterLink, GraphComponent, FormsModule, HttpClientModule],
    templateUrl: './main-page.component.html',
    styleUrl: './main-page.component.css'

})
export class MainPageComponent implements OnInit {
    selectedR: number = 2;
    x: number = 0;
    y: number = 0;
    @ViewChild(GraphComponent) graphComponent!: GraphComponent;
    currentUser = '';



    constructor(private apiService: ApiService, public utils: UtilsComponent,
                private authService: AuthService, private router: Router) {
        if (!this.authService.isLoggedIn()) {
            this.router.navigate(['/']);
        }
    }

    ngOnInit() {
        this.utils.updateDisplayMode()
        this.fetchResults();
        this.currentUser = this.authService.getCurrentUser();
    }

    private fetchResults() {
        this.apiService.getResults().pipe(
            tap(results => {
                results.forEach(result => this.updateResultsTable(result));
            }),
            catchError(error => {
                console.error('Ошибка при получении результатов: ', error);
                return throwError(() => new Error(error.message || 'Server error'));
            })
        ).subscribe();
    }


    @HostListener('window:resize', ['$event'])
    onResize() {
        this.utils.updateDisplayMode();
    }

    onSubmit() {
        if (!this.utils.validateGraphInput(this.x, this.y) ||  !this.utils.validateR(this.selectedR)) {
            alert(!this.utils.validateR(this.selectedR) ? "Некорректное значение R" : "X, Y, R некорректно!");
            return;
        }
        const pointData = {
            x: this.x,
            y: this.y,
            r: this.selectedR,
            timezoneOffset: new Date().getTimezoneOffset()
        };

        this.apiService.sendPoint(pointData).pipe(
            tap(result => {
                this.updateResultsTable(result);
                this.graphComponent.updateGraphWithNewPoint(result);
            }),
            catchError(error => {
                alert("Ошибка при отправке точки, возможно сервер умер :C");
                console.error('Ошибка при обработке точки: ', error);
                return throwError(() => new Error(error.message || 'Server error'));
            })
        ).subscribe();


    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/']);
    }


     updateResultsTable(result: any) {
        const table = document.getElementById('resultsTable') as HTMLTableElement;
        const newRow = table.insertRow(1);

        const xCell = newRow.insertCell(0);
        const yCell = newRow.insertCell(1);
        const rCell = newRow.insertCell(2);
        const hitCell = newRow.insertCell(3);
        const checkTimeCell = newRow.insertCell(4);
        const submitTimeCell = newRow.insertCell(5);

        xCell.textContent = result.x.toString();
        yCell.textContent = result.y.toString();
        rCell.textContent = result.r.toString();
        hitCell.textContent = result.hit ? 'Да' : 'Нет';
        checkTimeCell.textContent = result.checkTime;
        submitTimeCell.textContent = result.submitTime;
    }

}
