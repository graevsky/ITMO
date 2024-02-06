import {Component, HostListener, Injectable, OnInit} from '@angular/core';

@Component({
    selector: 'app-utils',
    templateUrl: './utils.component.html',
    styleUrl: './utils.component.css'
})

@Injectable({
    providedIn: 'root'
})

export class UtilsComponent implements OnInit {
    constructor() {
    }

    ngOnInit() {
        this.updateDisplayMode();
    }

    validateX(x: number): boolean{
      return x >= -3 && x <= 5;
    }
    validateY(y: number): boolean{
      return y >= -5 && y <= 5;
    }

    validateGraphInput(x: number, y: number): boolean {
        return x >= -3 && x <= 5 && y >= -5 && y <= 5;
    }

    validateR(r: number): boolean {
        return r >= 1 && r <= 5;
    }

    updateDisplayMode(): void {
        const width = window.innerWidth;
        const displayModeElement = document.getElementById('displayMode');
        if (width >= 1261) {
            // @ts-ignore
            displayModeElement.textContent = 'Режим отображения: десктопный';
        } else if (width >= 746) {
            // @ts-ignore
            displayModeElement.textContent = 'Режим отображения: планшетный';
        } else {
            // @ts-ignore
            displayModeElement.textContent = 'Режим отображения: мобильный';
        }
    }

}
