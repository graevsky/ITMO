import {Component, ElementRef, ViewChild, AfterViewInit, Input, Output, EventEmitter} from '@angular/core';
import {ApiService} from "../api.service";
import {UtilsComponent} from "../utils/utils.component";

@Component({
    selector: 'app-graph',
    standalone: true,
    templateUrl: './graph.component.html',
    styleUrls: ['./graph.component.css']
})
export class GraphComponent implements AfterViewInit {
    @ViewChild('canvas', {static: true}) canvas!: ElementRef<HTMLCanvasElement>;
    private ctx!: CanvasRenderingContext2D;
    private readonly MAX_R = 5;
    private readonly MAX_X = 4;
    private rVal = 2;

    @Input() set rValue(value: number) {
        if (this.utils.validateR(value)) {
            this.drawGraph(value);
            this.drawPoints();
            this.rVal = value;
        } else {
            this.drawPoints();
            this.rVal = value;
        }
    }


    points: any[] = [];


    constructor(private apiService: ApiService, private utils: UtilsComponent) {
    }

    @Output() pointAdded = new EventEmitter<any>();


    ngAfterViewInit(): void {
        this.ctx = this.canvas.nativeElement.getContext('2d')!;
        this.drawGraph(2);
        this.fetchPoints();
        this.canvas.nativeElement.addEventListener('click', this.onCanvasClick.bind(this));

    }

    fetchPoints(): void {
        this.apiService.getResults().subscribe(data => {
            this.points = data;
            this.drawPoints();
        });
    }

    private drawGraph(rValue: number) {
        const scaleFactor = this.canvas.nativeElement.width / (2 * (this.MAX_R + this.MAX_X));
        this.ctx.clearRect(0, 0, this.canvas.nativeElement.width, this.canvas.nativeElement.height);

        this.ctx.strokeStyle = "black";
        this.ctx.lineWidth = 2;
        this.ctx.beginPath();
        this.ctx.moveTo(0, this.canvas.nativeElement.height / 2);
        this.ctx.lineTo(this.canvas.nativeElement.width, this.canvas.nativeElement.height / 2);
        this.ctx.moveTo(this.canvas.nativeElement.width / 2, 0);
        this.ctx.lineTo(this.canvas.nativeElement.width / 2, this.canvas.nativeElement.height);
        this.ctx.stroke();

        this.ctx.fillStyle = "rgba(91,95,201,0.58)";

        //square
        this.ctx.fillRect(this.canvas.nativeElement.width / 2, this.canvas.nativeElement.height / 2, -rValue * scaleFactor, rValue / 2 * scaleFactor);

        this.ctx.beginPath();//circle
        this.ctx.moveTo(this.canvas.nativeElement.width / 2, this.canvas.nativeElement.height / 2);
        this.ctx.arc(this.canvas.nativeElement.width / 2, this.canvas.nativeElement.height / 2, rValue * scaleFactor, 0, Math.PI / 2, false);
        this.ctx.closePath();
        this.ctx.fill();


        this.ctx.beginPath(); //triangle
        this.ctx.moveTo(this.canvas.nativeElement.width / 2, this.canvas.nativeElement.height / 2);
        this.ctx.lineTo(this.canvas.nativeElement.width / 2 + rValue * scaleFactor / 2, this.canvas.nativeElement.height / 2);
        this.ctx.lineTo(this.canvas.nativeElement.width / 2, this.canvas.nativeElement.height / 2 - rValue * scaleFactor);
        this.ctx.closePath();
        this.ctx.fill();


        //marks
        this.ctx.fillStyle = "black";
        this.ctx.font = "14px Arial";
        this.ctx.fillText("R", this.canvas.nativeElement.width / 2 + rValue * scaleFactor, this.canvas.nativeElement.height / 2 + 20);
        this.ctx.fillText("R/2", this.canvas.nativeElement.width / 2 + rValue * 0.5 * scaleFactor, this.canvas.nativeElement.height / 2 + 20);
        this.ctx.fillText("-R", this.canvas.nativeElement.width / 2 - rValue * scaleFactor, this.canvas.nativeElement.height / 2 + 20);
        this.ctx.fillText("-R/2", this.canvas.nativeElement.width / 2 - rValue * 0.5 * scaleFactor, this.canvas.nativeElement.height / 2 + 20);
        this.ctx.fillText("R", this.canvas.nativeElement.width / 2 - 10, this.canvas.nativeElement.height / 2 - rValue * scaleFactor);
        this.ctx.fillText("R/2", this.canvas.nativeElement.width / 2 - 10, this.canvas.nativeElement.height / 2 - rValue * 0.5 * scaleFactor);
        this.ctx.fillText("-R", this.canvas.nativeElement.width / 2 - 10, this.canvas.nativeElement.height / 2 + rValue * scaleFactor);
        this.ctx.fillText("-R/2", this.canvas.nativeElement.width / 2 - 10, this.canvas.nativeElement.height / 2 + rValue * 0.5 * scaleFactor);
    }

    drawPoint(x: number, y: number, isHit: boolean): void {
        const scaleFactor = this.canvas.nativeElement.width / (2 * (this.MAX_R + this.MAX_X));
        let pixelX = this.canvas.nativeElement.width / 2 + x * scaleFactor;
        let pixelY = this.canvas.nativeElement.height / 2 - y * scaleFactor;

        this.ctx.fillStyle = isHit ? "green" : "red";
        this.ctx.beginPath();
        this.ctx.arc(pixelX, pixelY, 5, 0, 2 * Math.PI);
        this.ctx.fill();
    }

    drawPoints(): void {
        this.points.forEach(point => {
            this.drawPoint(point.x, point.y, point.hit);
        });
    }

    updateGraphWithNewPoint(newPoint: any) {
        this.points.push(newPoint);
        this.drawPoints();
    }


    onCanvasClick(event: MouseEvent): void {
        const rect = this.canvas.nativeElement.getBoundingClientRect();
        const displayScaleFactorX = this.canvas.nativeElement.width / rect.width;
        const displayScaleFactorY = this.canvas.nativeElement.height / rect.height;

        const x = (event.clientX - rect.left) * displayScaleFactorX;
        const y = (event.clientY - rect.top) * displayScaleFactorY;

        const graphScaleFactor = this.canvas.nativeElement.width / (2 * (this.MAX_R + this.MAX_X));
        const graphX = (x - this.canvas.nativeElement.width / 2) / graphScaleFactor;
        const graphY = (this.canvas.nativeElement.height / 2 - y) / graphScaleFactor;

        if (this.utils.validateGraphInput(graphX, graphY) && this.utils.validateR(this.rVal)) {
            const pointData = {
                x: graphX,
                y: graphY,
                r: this.rVal,
                timezoneOffset: new Date().getTimezoneOffset()
            };
            this.apiService.sendPoint(pointData).subscribe(
                result => {
                    this.pointAdded.emit(result);
                    this.updateGraphWithNewPoint(result);
                },
                error => {
                  alert("Ошибка при отправке точки, возможно сервер умер :C");
                  console.error('Ошибка при обработке точки: ', error);

                }
            );
        } else {
            alert(this.utils.validateR(this.rVal) ? "R некорректно" : "Точка за пределами допустимых координат!");
        }
    }


}
