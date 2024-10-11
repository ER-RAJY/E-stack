import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditApprenantDialogComponentComponent } from './edit-apprenant-dialog-component.component';

describe('EditApprenantDialogComponentComponent', () => {
  let component: EditApprenantDialogComponentComponent;
  let fixture: ComponentFixture<EditApprenantDialogComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditApprenantDialogComponentComponent]
    });
    fixture = TestBed.createComponent(EditApprenantDialogComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
