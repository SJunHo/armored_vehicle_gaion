

import { connect } from "react-redux";
import React, { Component } from 'react';

class BookmarkBtn extends Component {
    constructor(props) {
        super(props);
        this.clickBtn = this.clickBtn.bind(this);
        this.checkBookmark = this.checkBookmark.bind(this);
        this.state = {
            isClicked: false,
            bookmarkList: [],
        }
    }

    componentDidMount() {
        console.log("DIDMount================");
        console.log(this.props);
        // this.setState({
        //     bookmarkList: this.props.bookmark
        // }, () => {
        //     // console.log(this.props);
        //     console.log(this.state.bookmarkList);
        //     console.log(this.props.content.code);
        //     this.state.bookmarkList.forEach((el, idx) => {
        //         if(el.snsrid === this.props.content.code){
        //             this.setState({
        //                 isClicked: true,
        //             })
        //         }
        //     })



        // });
    }
    componentDidUpdate(prevProps, prevState) {
        if(prevProps.bookmark !== this.props.bookmark){
            if(this.props.bookmark.length > 0){
                this.setState({
                    bookmarkList: this.props.bookmark
                }, () => {
                    this.state.bookmarkList.forEach((el, idx) => {
                        if(el.snsrid === this.props.content.code){
                            this.setState({
                                isClicked: true,
                            })
                            let param = [];
                            param.push(this.props.content.code);
                            param.push(this.props.content.var);
                            param.push(this.props.content.expln);
                            this.props.setBtnFunc(param);
                        }
                    })
                });
            }else{
                this.setState({
                    isClicked: false
                })
            }
        } 

    }

    clickBtn(){
        let param = [];
        this.setState({
            isClicked : !this.state.isClicked
        })
        console.log(this.props.content);
        console.log(this.state.isClicked);
        console.log(this.props.content.code);
        if(this.props.name === "nummeric"){
            if(this.state.isClicked){
                param.push(-1);
                param.push(this.props.content.var);
                param.push(this.props.content.code);
                param.push(this.props.content.expln);
                console.log(param);
                this.props.setBookmark(param);
            }else{
                param.push(1);
                param.push(this.props.content.var);
                param.push(this.props.content.code);
                param.push(this.props.content.expln);
                console.log(param);
                this.props.setBookmark(param);
            }
        }else{
            if(this.state.isClicked){
                param.push(-1);
                param.push(this.props.content.var);
                param.push(this.props.content.code);
                param.push(this.props.content.expln);
                console.log(param);
                this.props.setBookmark(param);
            }else{
                param.push(1);
                param.push(this.props.content.var);
                param.push(this.props.content.code);
                param.push(this.props.content.expln);
                console.log(param);
                this.props.setBookmark(param);
            }
        }
    

        

    }

    checkBookmark(){

    }

    render() {
        const {isClicked} = this.state;
        const {content} =this.props;
        return (
            <button 
                className={`buttonList ${isClicked? 'blue': 'red'}`}
                id={content.code}
                style={{backgroundColor: isClicked ? 'blue' : 'red'}}
                onClick={this.clickBtn} 
                >
                {content.expln}
            </button>
        );
    }
}


export default connect(null, {
})(BookmarkBtn);
