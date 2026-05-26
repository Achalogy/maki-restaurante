import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable()
export class ApiUrlInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // If the request points to localhost:8080, rewrite it to the configured environment.apiUrl
    if (request.url.includes('http://localhost:8080')) {
      const updatedUrl = request.url.replace('http://localhost:8080', environment.apiUrl);
      request = request.clone({
        url: updatedUrl
      });
    }
    return next.handle(request);
  }
}
