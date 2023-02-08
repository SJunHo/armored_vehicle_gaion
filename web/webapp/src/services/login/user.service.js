import axios from "axios";
import authHeader from "./auth-header";
import { BASE_URL } from "../url";

const API_URL = BASE_URL + "/api/user";

class UserService {
	get(id) {
		return axios.get(API_URL + `/info/${id}`, {headers: authHeader(), "Content-type": "application/json",});
	}

	getUserList(params) {
		return axios.get(API_URL + "/list", {headers: authHeader(), params});
	}

	update(data) {
		return axios.post(API_URL + `/update`, data, {headers: authHeader(), "Content-type": "application/json",});
	}

	delete(id) {
		return axios.get(API_URL + `/delete/${id}`, {headers: authHeader(), "Content-type": "application/json",});
	}

	getDivsList() {
		return axios.get(API_URL + `/divsList`, { headers: authHeader(),  });
	}
	
	getBnList(data) {
	return axios.get(API_URL + `/bnList/${data}`, { headers: authHeader(),'Content-type': "application/json",});
	}

	getUserLogList(params) {
		return axios.get(API_URL + "/getUserLog", {headers: authHeader(), params});
	}

}

export default new UserService();
