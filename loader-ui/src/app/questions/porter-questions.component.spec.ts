import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PorterQuestionsComponent } from './porter-questions.component';

describe('QuestionsComponent', () => {
  let component: PorterQuestionsComponent;
  let fixture: ComponentFixture<PorterQuestionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PorterQuestionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PorterQuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
