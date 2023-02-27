import axios from "axios";
import authHeader from "../login/auth-header";
import { BASE_URL } from "../url";

const API_URL = BASE_URL + "/api/statusdatalookup";

class StatusDataLookupDataService {
    getAllVehicleInfo(){
        return axios.get( API_URL + "/getAllVehicle", {headers: authHeader(), 'Content-type': "application/json", });
    }

    searchTroubleBer(data){
        return axios.post( API_URL + "/searchStatusBer", data, {headers: authHeader(), "Content-type": "application/json", });
    }
    searchTroubleEng(data){
        return axios.post( API_URL + "/searchStatusEng", data, {headers: authHeader(), "Content-type": "application/json", });
    }
    searchTroubleGrb(data){
        return axios.post( API_URL + "/searchStatusGrb", data, {headers: authHeader(), "Content-type": "application/json", });
    }
    searchTroubleWhl(data){
        return axios.post( API_URL + "/searchStatusWhl", data, {headers: authHeader(), "Content-type": "application/json", });
    }
    downloadExcel(data){
        return axios.post( API_URL + "/downloadExcel", data , { headers: authHeader() , "Content-type": "application/json", responseType : 'blob'});
    }
}

export default new StatusDataLookupDataService();