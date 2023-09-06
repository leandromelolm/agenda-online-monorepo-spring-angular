import { Component } from '@angular/core';
import {MatCardModule} from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { AgendaService } from 'src/app/service/agenda.service';


@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.css'],
  standalone: true,
  imports: [MatCardModule, MatDatepickerModule, MatNativeDateModule],
})
export class DatepickerComponent {

  selectedDate : Date = new Date();

  constructor(
    private agendaService : AgendaService
  ){}

  callAgendaService(arg: any){
    this.agendaService.changeDateInFullcalendar(arg);
  }

}
