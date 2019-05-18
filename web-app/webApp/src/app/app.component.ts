import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  idStudent: string;
  nume: string;
  grupa: number;
  email: string;
  profesor: string;

  constructor(private httpClient: HttpClient){

  }

  ngOnInit(): void {
  }


  onAddStudent() {
    let student = {idStudent: this.idStudent, nume: this.nume, grupa: this.grupa, email: this.email, profesor: this.profesor};
    this.httpClient.post<any>("http://localhost:8080/students", student).subscribe(
      student => console.log(student),
      error => console.log(error)
    );
  }
}
