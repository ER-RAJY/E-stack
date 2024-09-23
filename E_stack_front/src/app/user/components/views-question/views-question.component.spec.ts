import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewsQuestionComponent } from './views-question.component';

describe('ViewsQuestionComponent', () => {
  let component: ViewsQuestionComponent;
  let fixture: ComponentFixture<ViewsQuestionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewsQuestionComponent]
    });
    fixture = TestBed.createComponent(ViewsQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
