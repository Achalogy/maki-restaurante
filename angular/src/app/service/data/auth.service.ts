import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { Router } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private baseUrl = "http://localhost:8080/api/v1/auth";
  private currentUserSubject: BehaviorSubject<object | null> =
    new BehaviorSubject<object | null>(null);

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {
    this.loadUserFromStorage();
  }

  private loadUserFromStorage() {
    const token = localStorage.getItem("jwt_token");
    const role = localStorage.getItem("user_role");
    const id = localStorage.getItem("user_id");
    const username = localStorage.getItem("username");

    if (token && role) {
      this.currentUserSubject.next({ token, role, id, username });
    }
  }

  login(credentials: { username: string; password: string }): Observable<{
    token: string;
    role: string;
    username: string;
    id: string;
  }> {
    return this.http
      .post<{
        token: string;
        role: string;
        username: string;
        id: string;
      }>(`${this.baseUrl}/log-in`, credentials)
      .pipe(
        tap((response) => {
          if (response && response.token) {
            localStorage.setItem("jwt_token", response.token);
            localStorage.setItem("user_role", response.role);
            localStorage.setItem("username", response.username);
            if (response.id) {
              localStorage.setItem("user_id", response.id);
            }
            this.currentUserSubject.next(response);
          }
        }),
      );
  }

  logout() {
    localStorage.removeItem("jwt_token");
    localStorage.removeItem("user_role");
    localStorage.removeItem("user_id");
    localStorage.removeItem("username");

    // clear legacy items if they exist
    localStorage.removeItem("loggedAs");
    localStorage.removeItem("id");

    this.currentUserSubject.next(null);
    this.router.navigate(["/"]);
  }

  get currentUserValue() {
    return this.currentUserSubject.value;
  }

  getToken(): string | null {
    return localStorage.getItem("jwt_token");
  }

  getRole(): string | null {
    return localStorage.getItem("user_role");
  }

  getUserId(): string | null {
    return localStorage.getItem("user_id");
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  redirectBasedOnRole() {
    const role = this.getRole();
    const id = this.getUserId();
    if (role === "ADMIN") {
      this.router.navigate(["/admin"]);
    } else if (role === "OPERATOR") {
      this.router.navigate(["/operator/gateway"]);
    } else if (role === "CLIENT") {
      if (id) {
        this.router.navigate([`/client`]);
      } else {
        this.router.navigate(["/"]);
      }
    }
  }
}
