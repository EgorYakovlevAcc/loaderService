import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCustomersComponent } from './user-customers.component';

describe('UserCustomersComponent', () => {
  let component: UserCustomersComponent;
  let fixture: ComponentFixture<UserCustomersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserCustomersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserCustomersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
