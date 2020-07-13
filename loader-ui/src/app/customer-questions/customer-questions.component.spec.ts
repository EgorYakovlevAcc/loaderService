import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerQuestionsComponent } from './customer-questions.component';

describe('CustomerQuestionsComponent', () => {
  let component: CustomerQuestionsComponent;
  let fixture: ComponentFixture<CustomerQuestionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CustomerQuestionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CustomerQuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
