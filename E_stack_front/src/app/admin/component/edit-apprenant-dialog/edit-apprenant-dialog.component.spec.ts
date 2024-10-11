import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditApprenantDialogComponent } from './edit-apprenant-dialog.component';

describe('EditApprenantDialogComponent', () => {
  let component: EditApprenantDialogComponent;
  let fixture: ComponentFixture<EditApprenantDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditApprenantDialogComponent]
    });
    fixture = TestBed.createComponent(EditApprenantDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
