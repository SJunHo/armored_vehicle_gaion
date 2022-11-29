import axios from "axios";
import authHeader from "../login/auth-header";
import { BASE_URL } from "../url";

const API_URL = BASE_URL +"/api/monitordiagnost";

class monitorDiagnostDataService {
    getAllVehicleInfo(){
        return axios.get( API_URL + "/getAllVehicle", {headers: authHeader(), 'Content-type': "application/json", });
    }

    searchTroubleBer(data){
        return axios.post( API_URL + "/searchTroubleBer", data, {headers: authHeader(), "Content-type": "application/json", });
    }
    searchTroubleEng(data){
        return axios.post( API_URL + "/searchTroubleEng", data, {headers: authHeader(), "Content-type": "application/json", });
    }
    searchTroubleGrb(data){
        return axios.post( API_URL + "/searchTroubleGrb", data, {headers: authHeader(), "Content-type": "application/json", });
    }
    searchTroubleWhl(data){
        return axios.post( API_URL + "/searchTroubleWhl", data, {headers: authHeader(), "Content-type": "application/json", });
    }

    downloadExcel(data){
        return axios.post( API_URL + "/downloadExcel", data , { headers: authHeader() , "Content-type": "application/json", responseType : 'blob'});
    }

    searchAllTroubleChartData(data){
        return axios.post( API_URL + "/searchAllTroubleChartData", data, {headers: authHeader(), "Content-type" : "application/json", });
    }

    searchParamdesc(data){
        return axios.post( API_URL + "/searchParamdesc", data, {headers: authHeader(), "Content-type" : "application/json", });
    }

    getThreshold() {
        return axios.get( API_URL + "/getThreshold", { headers: authHeader() , "Content-type": "application/json", });
    }

    searchSimulation(data){
        return axios.post( API_URL + "/searchSimulation", data, {headers: authHeader(), "Content-type": "application/json", });
    }
}

export default new monitorDiagnostDataService();