import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavigationComponent} from './navigation/navigation.component';
import {RouterModule, Routes} from "@angular/router";
import {PorterQuestionsComponent} from './questions/porter-questions.component';
import {UsersComponent} from './users/users.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {MainpageComponent} from './mainpage/mainpage.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { QuestionComponent } from './question/question.component';
import { ModalAddQuestionComponent } from './modal-add-question/modal-add-question.component';
import { MessageFormComponent } from './message-form/message-form.component';
import { AboutComponent } from './about/about.component';
import { FirstAndLastMessagesEditorComponent } from './first-and-last-messages-editor/first-and-last-messages-editor.component';
import { ScoreRangeMessageComponent } from './score-range-message/score-range-message.component';
import { CustomerQuestionsComponent } from './customer-questions/customer-questions.component';
import { UserCustomersComponent } from './user-customers/user-customers.component';
import { AdminUsersComponent } from './admin-users/admin-users.component';
import { ModalAddAdminComponent } from './modal-add-admin/modal-add-admin.component';

const appRoutes: Routes = [
  {
    path: 'questions-porter',
    component: PorterQuestionsComponent
  },
  {
    path: 'questions-customer',
    component: CustomerQuestionsComponent
  },
  {
    path: 'porters',
    component: UsersComponent
  },
  {
    path: 'customers',
    component: UserCustomersComponent
  },
  {
    path: 'admins',
    component: AdminUsersComponent
  },
  {
    path: '',
    component: MainpageComponent,
    pathMatch: 'full'
  },
  {
    path: 'questions/add',
    component: QuestionComponent
  },
  {
    path: 'global/message',
    component: MessageFormComponent
  },
  {
    path: 'range/message',
    component: ScoreRangeMessageComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    PorterQuestionsComponent,
    UsersComponent,
    NotFoundComponent,
    MainpageComponent,
    QuestionComponent,
    ModalAddQuestionComponent,
    MessageFormComponent,
    AboutComponent,
    FirstAndLastMessagesEditorComponent,
    ScoreRangeMessageComponent,
    CustomerQuestionsComponent,
    UserCustomersComponent,
    AdminUsersComponent,
    ModalAddAdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
