(window.webpackJsonp_dataexporler=window.webpackJsonp_dataexporler||[]).push([[18],{"2d45":function(t,e,i){"use strict";i.r(e);i("d3b7"),i("3ca3"),i("ddb0"),i("2b3d"),i("e260");var a={name:"Uac",data:function(){return{title:this.$t("public.uacErrorTitle"),description:this.$t("public.uacErrorDescription"),loading:this.$t("public.loading"),isError:!1}},created:function(){var t=this,e=t.$uac.url+"/ticketcollect?realmcode="+t.$uac.realmCode+"&r="+Math.random(),i=new Blob(["\n          var UAC = {\n            run: function () {\n              if (this.timer) clearTimeout(this.timer);\n              uacCookie.success = true;\n              self.postMessage(uacCookie);\n            },\n            timer: null\n          }\n          UAC.timer = setTimeout(function () {\n            self.postMessage({ success: false });\n          }, 3000);\n          importScripts('".concat(e,"');\n        ")],{type:"application/javascript"}),a=URL.createObjectURL(i);new Worker(a).onmessage=function(e){e.data.success?e.data._token?t.$http({url:t.$uac.url+"/v1/ticketvalid",method:"post",headers:{"Semf-Ticket":e.data._token},params:{realmCode:t.$uac.realmCode,clientId:t.$uac.clientId}}).then((function(){document.cookie=t.$uac.realmCode+"_UACTOKEN="+e.data._token+"; max-age=432000; path=/",t.$router.push("/")})).catch((function(){window.location.href=t.$uac.url+"/ssologin?redirect="+encodeURIComponent(window.location)+"&realmcode="+t.$uac.realmCode+"&clientid="+t.$uac.clientId})):window.location.href=t.$uac.url+"/ssologin?redirect="+encodeURIComponent(window.location)+"&realmcode="+t.$uac.realmCode+"&clientid="+t.$uac.clientId:t.isError=!0}}},c=(i("ff07"),i("2877")),o=Object(c.a)(a,(function(){var t=this.$createElement,e=this._self._c||t;return e("div",{staticClass:"uac"},[this.isError?e("div",{staticClass:"uac-error"},[e("div",{staticClass:"exception-content"},[e("img",{staticClass:"imgException",attrs:{src:i("df40")}}),e("div",[e("h3",{staticClass:"title",domProps:{textContent:this._s(this.title)}}),e("p",{staticClass:"description",domProps:{innerHTML:this._s(this.description)}})])])]):e("div",{staticClass:"uac-loading"},[e("div",{staticClass:"exception-content"},[e("img",{staticClass:"imgException",attrs:{src:i("ac31")}}),e("div",[e("h3",{staticClass:"title",domProps:{textContent:this._s(this.loading)}})])])])])}),[],!1,null,"674df6a8",null);e.default=o.exports},ac31:function(t,e,i){t.exports=i.p+"img/uac-boot-loading.12ae6e0d.gif"},bfeb:function(t,e,i){},ff07:function(t,e,i){"use strict";var a=i("bfeb");i.n(a).a}}]);