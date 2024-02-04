export interface CommonResponse {
  status: string;
}

export interface CommonStringResponse extends CommonResponse {
  data: string;
}

export interface Token {
  user_id: number;
  token: string;
}

export interface FirebaseTokenResponse extends CommonResponse {
  data: Token[];
}
