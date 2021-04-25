import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export enum RequestMethod {
  Get = 'GET',
  Post = 'POST',
}

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private readonly defaultHeaders = new HttpHeaders({
    Accept: 'application/json',
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) {
  }

  get(url: string, headers?: HttpHeaders, params?: HttpParams): Observable<any> {
    return this.request(url, RequestMethod.Get, null, headers, params);
  }

  post(url: string, body?: any, headers?: HttpHeaders): Observable<any> {
    return this.request(url, RequestMethod.Post, body, headers);
  }

  private request(
    url: string,
    method: RequestMethod,
    body?: any,
    headers?: HttpHeaders,
    params?: HttpParams
  ): Observable<any> {
    return this.http.request(method, url, {
      headers: headers || this.defaultHeaders,
      params,
      body,
      withCredentials: true,
      observe: 'response'
    });
  }
}
