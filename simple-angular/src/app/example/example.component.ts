import { Component, OnInit } from '@angular/core';
import { ExampleService } from './example.service';

@Component({
  selector: 'app-example',
  templateUrl: './example.component.html',
  styleUrls: ['./example.component.css']
})
export class ExampleComponent implements OnInit {

  constructor(
    private exampleService: ExampleService
  ) {

  }

  ngOnInit() {
  }

}
