<template>
    <el-dialog
    title="拍摄照片"
    :visible="show"
    width="600px"
    :before-close="handleClose">
        <div class="photo-picker-container">
            <div class="photo-picker-camera">
                <video class="photo-picker-video" id="video" width="400px" height="400px" autoplay="autoplay"></video>
                <canvas class="photo-picker-canvas" id="canvas" width="400px" height="400px"></canvas>
            </div>
            <div v-show="false" class="photo-picker-preview">
                <div class="photo-picker-big">
                    <canvas class="photo-picker-canvas100" id="canvas100" width="400px" height="400px"></canvas>
                </div>
                <!-- <div class="photo-picker-normal">
                    <canvas class="photo-picker-canvas75" id="canvas75" width="75px" height="75px"></canvas>
                </div>
                <div class="photo-picker-small">
                    <canvas class="photo-picker-canvas50" id="canvas50" width="50px" height="50px"></canvas>                    
                </div> -->
            </div>
        </div>
        <div class="photo-picker-controller">
            <el-button @click="resetPhoto">重置</el-button>
            <el-button v-if="mode == 1" type="success" @click="takePhoto">拍照</el-button>
            <el-button v-else type="primary" @click="snapShot">确定</el-button>
        </div>
    </el-dialog>
</template>

<script>
export default {
    props:{
        show:Boolean
    },
    data(){
        return{
            timer:null,
            protoX:0,
            protoY:0,
            protoDownX:0,
            protoDownY:0,
            widtheigh:400,
            mouseDownX:0,
            mouseDownY:0,
            gap:0,
            mode:1,
            selectedCorner:'',
            track:''
        }
    },
    watch: {
        show:function(){
            this.getMedia()
        }
    },
    methods: {
        handleClose(){
            this.$emit('close')
        },
        getMedia() {
            let _this = this
            let constraints = {
                video: {width: 400, height: 400},
                audio: true
            };
            let promise = navigator.mediaDevices.getUserMedia(constraints);
            promise.then(function (MediaStream) {
                console.log(MediaStream);
                console.log(video);
                _this.track = MediaStream
                video.srcObject = MediaStream;
                video.play();
            }).catch(function (PermissionDeniedError) {
                console.log(PermissionDeniedError.message);
                console.log(PermissionDeniedError);
            })
        },
        takePhoto() {
            //获得Canvas对象
            let canvas = document.getElementById("canvas");
            let ctx = canvas.getContext('2d');
            video.pause();
            this.drawCover(canvas,ctx)
            this.mode = 2
            this.track.getTracks()[1].stop()
        },
        drawCover(canvas,ctx) {
            let canvas100 = document.getElementById("canvas100");
            let ctx100 = canvas100.getContext('2d');
            let offset = 0
            this.photoHandler(canvas,ctx)
            ctx.save();
            this.timer = setInterval(() => {
                ctx100.drawImage(video, this.protoX, this.protoY, this.widtheigh, this.widtheigh,0,0,400,400);
                // ctx75.drawImage(video, this.protoX, this.protoY, this.widtheigh, this.widtheigh,0,0,75,75);
                // ctx50.drawImage(video, this.protoX, this.protoY, this.widtheigh, this.widtheigh,0,0,50,50);
                ctx.clearRect(0,0, canvas.width, canvas.height);
                ctx.fillStyle = 'rgba(0, 0, 0, 0.3)';
                ctx.fillRect(0, 0, canvas.width, canvas.height);
                ctx.clearRect(this.protoX, this.protoY, this.widtheigh, this.widtheigh);
                ctx.setLineDash([5,5]);
                ctx.lineWidth = 1;
                ctx.strokeStyle="blue";
                ctx.lineDashOffset = -offset;
                ctx.strokeRect(this.protoX, this.protoY, this.widtheigh, this.widtheigh);
                if(offset > 16){
                    offset = 0;
                }
                offset+=2;
                this.photoSelector(canvas,ctx)
            },40)
            ctx.restore();
        },
        photoHandler(canvas,ctx){
            canvas.addEventListener('mousedown',(params) => {
                this.mouseDownX = params.layerX
                this.mouseDownY = params.layerY
                this.protoDownX = this.protoX
                this.protoDownY = this.protoY
                // if(params.layerX < this.protoX +25 && params.layerX > this.protoX - 25){
                //     if(params.layerY < this.protoY + 25 && params.layerY > this.protoY - 25){
                //         console.log('lt')
                //         this.selectedCorner = 'lt'
                //     }else if(params.layerY > this.protoY + this.widtheigh - 25 && params.layerY < this.protoY + this.widtheigh + 25){
                //         console.log('lb')
                //         this.selectedCorner = 'lb'
                //     }
                // }else if(params.layerX > this.protoX + this.widtheigh - 25 && params.layerX < this.protoX + this.widtheigh + 25){
                //     if(params.layerY < this.protoY + 25 && params.layerY > this.protoY - 25){
                //         console.log('rt')
                //         this.selectedCorner = 'rt'
                //     }else if(params.layerY > this.protoY + this.widtheigh - 25 && params.layerY < this.protoY + this.widtheigh + 25){
                //         console.log('rb')
                //         this.selectedCorner = 'rb'
                //     }
                // }
                this.gap = this.widtheigh
                if(ctx.isPointInStroke(params.layerX,params.layerY)){
                    canvas.addEventListener('mousemove',this.mouseMoveEvent)
                    canvas.addEventListener('mouseup',(upinfo)=>{
                        canvas.removeEventListener('mousemove',this.mouseMoveEvent)
                    })
                }else{
                    if(this.protoX < params.layerX && params.layerX < this.protoX + this.widtheigh && this.protoY < params.layerY && params.layerY < this.protoY + this.widtheigh){
                        canvas.addEventListener('mousemove',this.mouseDragEvent)
                        canvas.addEventListener('mouseup',(upinfo)=>{
                            canvas.removeEventListener('mousemove',this.mouseDragEvent)
                        })
                    }else{
                        console.log(false)
                    }
                } 
            })
        },
        mouseMoveEvent(params){
            if(params.layerX >params.layerY){
                // switch(this.selectedCorner){
                //     case 'lt':
                //         this.gap = 400
                //         this.protoX = this.protoDownX + params.layerX - this.mouseDownX
                //         this.protoY = this.protoDownX + params.layerX - this.mouseDownX
                //         this.widtheigh = this.gap - params.layerX - this.mouseDownX
                //         break
                // }
                this.widtheigh = this.gap + params.layerX - this.mouseDownX
            }else{
                // switch(this.selectedCorner){
                //     case 'lt':
                //         this.protoX = this.protoDownY + params.layerY - this.mouseDownY
                //         this.protoY = this.protoDownY + params.layerY - this.mouseDownY
                //         this.widtheigh = this.gap - params.layerY - this.mouseDownY
                //         break
                // }
                this.widtheigh = this.gap + params.layerY - this.mouseDownY
            }
            if(this.widtheigh>400){
                this.widtheigh = 400
            }
            if(this.widtheigh < 100){
                 this.widtheigh = 100
            }
        },
        mouseDragEvent(params){
            this.protoX = this.protoDownX + params.layerX - this.mouseDownX
            this.protoY = this.protoDownY + params.layerY - this.mouseDownY
            if(this.protoX + this.widtheigh>400){
                this.protoX = 400 -this.widtheigh
            }
            if(this.protoX < 0){
                this.protoX = 0
            }
            if(this.protoY + this.widtheigh>400){
                this.protoY = 400 -this.widtheigh
            }
            if(this.protoY < 0){
                this.protoY = 0
            }
        },
        snapShot(){
            let canvas100 = document.getElementById("canvas100");
            var base64Data = canvas100.toDataURL("image/jpeg", 1.0);
            var blob = this.dataURItoBlob(base64Data);
            this.$emit('snapShotOk',{base64:base64Data,blob})
            this.resetPhoto()
        },
        dataURItoBlob (base64Data) {
            var byteString;
            if (base64Data.split(',')[0].indexOf('base64') >= 0)
                byteString = atob(base64Data.split(',')[1]);
            else
                byteString = unescape(base64Data.split(',')[1]);
            var mimeString = base64Data.split(',')[0].split(':')[1].split(';')[0];
            var ia = new Uint8Array(byteString.length);
            for (var i = 0; i < byteString.length; i++) {
                ia[i] = byteString.charCodeAt(i);
            }
            return new Blob([ia], {type: mimeString});
        },
        photoSelector(canvas,ctx){
            ctx.beginPath();
            ctx.setLineDash([0,0])
            ctx.lineWidth = 5;
            // 左上角
            // ctx.moveTo(this.protoX,this.protoY+25)
            // ctx.lineTo(this.protoX,this.protoY-2)
            // ctx.moveTo(this.protoX-2,this.protoY)
            // ctx.lineTo(this.protoX+25,this.protoY)
            // // 右上角
            // ctx.moveTo(this.protoX+this.widtheigh-25,this.protoY)
            // ctx.lineTo(this.protoX+this.widtheigh+2,this.protoY)
            // ctx.moveTo(this.protoX+this.widtheigh,this.protoY+2)
            // ctx.lineTo(this.protoX+this.widtheigh,this.protoY+25)
            // 右下角
            ctx.moveTo(this.protoX+this.widtheigh-25,this.protoY+this.widtheigh-2)
            ctx.lineTo(this.protoX+this.widtheigh+2,this.protoY+this.widtheigh-2)
            ctx.moveTo(this.protoX+this.widtheigh-2,this.protoY+this.widtheigh+2)
            ctx.lineTo(this.protoX+this.widtheigh-2,this.protoY+this.widtheigh-25)
            // 左下角
            // ctx.moveTo(this.protoX,this.protoY+this.widtheigh-25)
            // ctx.lineTo(this.protoX,this.protoY+this.widtheigh+2)
            // ctx.moveTo(this.protoX-2,this.protoY+this.widtheigh)
            // ctx.lineTo(this.protoX+25,this.protoY+this.widtheigh)
            ctx.strokeStyle="blue";
            // 开始填充
            ctx.stroke();
            ctx.closePath();
        },
        resetPhoto(){
            let c=document.getElementById("canvas")
            let ctx = c.getContext("2d");
            ctx.clearRect(0,0,c.width,c.height);
            clearInterval(this.timer)
            this.timer = null
            this.mode = 1
            video.play()
        }
        
    }
}
</script>

<style>
.photo-picker-container{
    justify-content:center;
    display: flex;
}
.photo-picker-video{
    position: relative;
}
.photo-picker-canvas{
    position: absolute;
    left: 100px;
    cursor:move;
}
.photo-picker-big{
    width:100px;
    height:100px;
    background-color: #999;
}
.photo-picker-normal{
    width:75px;
    height: 75px;
    background-color: #999;
}
.photo-picker-small{
    width: 50px;
    height: 50px;
    background-color: #999;
}
.photo-picker-preview{
    display: flex;
    flex-direction: column;

    align-items: flex-end;
    margin: 0 auto;
}
.photo-picker-preview div{
    margin: 30px 0;
}
.photo-picker-controller{
    /* width:100%; */
    padding: 20px 100px;
    display: flex;
    justify-content:space-around;
}
</style>