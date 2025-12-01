# Library
Đặc tả bài toán: Hệ thống quản lý thư viện cho phép người dùng (User) đăng nhập với vai trò Member hoặc Librarian.
Các chức năng chính của hệ thống:
•	Quản lý người dùng (User, Member, Librarian)
•	Quản lý tài liệu (Document)
•	Kiểm tra số lượng tài liệu để phục vụ cho chức năng mượn/trả
•	Phân quyền dựa trên role
Bảng 1.1: Dữ liệu đầu vào User
Tên trường	Tên biến	Kiểu dữ liệu	Miền giá trị	Ràng buộc nghiệp vụ
Mã người dùng	userId	Số nguyên (int)	> 0	Duy nhất
Họ tên	fullName	Chuỗi	Không rỗng	Không chứa ký tự đặc biệt cấm
Tên đăng nhập	username	Chuỗi	Không rỗng	Duy nhất, độ dài hợp lệ theo thiết kế
Mật khẩu (đã hash)	passwordHash	Chuỗi SHA-512	128 ký tự hex	Không lưu mật khẩu dạng thô
Email	email	Chuỗi	Định dạng email	Duy nhất
Vai trò	– (abstract)	Chuỗi	“member” hoặc “librarian”	Xác định bằng lớp con

Bảng 1.2. Tài khoản Member
Tên trường	Tên biến	Giá trị	Ý nghĩa
Vai trò	MEMBER	Chuỗi "member"	Xác định người dùng là độc giả

Bảng 1.3. Tài khoản Librarian
Tên trường	Tên biến	Giá trị	Ý nghĩa
Vai trò	LIBRARIAN	Chuỗi "librarian"	Xác định người dùng là thủ thư

Bảng 1.4. Dữ liệu đầu vào Document
Tên trường	Tên biến	Kiểu	Miền giá trị	Ràng buộc
Mã tài liệu	documentId	int	> 0	Duy nhất
Tên tài liệu	title	String	Không rỗng	—
Tác giả	author	String	Không rỗng	—
Nhà xuất bản	publisher	String	Không rỗng	—
Mô tả	description	String	≤ 500 ký tự	Không bắt buộc
ISBN	isbn	String	Chuỗi 10–13 ký tự	Duy nhất
Ngôn ngữ	language	String	Ví dụ: "EN", "VN"	—
Số trang	pageCount	int	≥ 1	Bước nhảy ±1
Link ảnh bìa	CoverImageURL	String	URL hợp lệ	Không bắt buộc
Điểm đánh giá TB	averageRating	double	[0.0, 5.0]	—
Số lượng tồn kho	amount	int	≥ 0	Không âm

Bảng 1.5. Các kết quả có thể có khi thao tác trên hệ thống
Kết quả	Mô tả
Thông tin User	Sau đăng ký, đăng nhập
Thông tin Document	Hiển thị chi tiết tài liệu
Danh sách Document	Kết quả tìm kiếm / danh mục
Số lượng tồn	Hiển thị amount trong Document
Role phân quyền	“member” hoặc “librarian”

2. Yêu cầu phi chức năng
- Hiệu năng: Các thao tác được xử lý trong vòng dưới 5 giây
- Bảo mật: Mật khẩu phải dùng SHA-512
- Khả dụng: Hệ thống chạy 24/7

ĐẶC TẢ USE CASE

Use case ID	UC1	UC2	UC3	UC4
Use case name	Đăng ký tài khoản (Member Registration)	Đăng nhập (Login)	Khôi phục mật khẩu	Cập nhật thông tin cá nhân (Manage Profile)
Description	Cho phép độc giả (Member) đăng ký tài khoản để truy cập hệ thống thư viện và thực hiện mượn – trả tài liệu	Cho phép người dùng đăng nhập khi đã có tài khoản	Cho phép người dùng khôi phục mật khẩu khi quên	Cho phép đổi fullName, password.
Actor(s)	Member, Librarian	Member, Librarian	Member, Librarian	Member, Librarian
Priority	Must have	Must have	Must have	Should have
Trigger	Độc giả muốn sử dụng hệ thống để mượn tài liệu	Người dùng muốn truy cập Library System		
Pre-condition	- Email chưa tồn tại trong hệ thống.
- Thiết bị có kết nối Internet.
- Member chưa có tài khoản trong hệ thống.	- Tài khoản đã được tạo bằng constructor User hoặc subclass Member/Librarian.
- Người dùng chưa đăng nhập
	Email đã đăng ký	
Post-condition	Hệ thống tạo bản ghi User (role = “member”) theo constructor:
Member(userId, fullName, username, passwordHash, email)	- Hệ thống xác định role bằng getRole()
+ Member  “member”
+ Librarian “librarian”
- Phân quyền giao diện theo role
	Mật khẩu mới được cập nhật	
Basic flow	- Độc giả chọn mục “Đăng ký”.
- Hệ thống hiển thị form đăng ký gồm: họ tên, username, mật khẩu, email.
- Độc giả nhập thông tin vào form và chọn “Đăng ký”.
- Hệ thống xác thực email và username theo dữ liệu Database.
- Hệ thống mã hóa mật khẩu bằng SHA-512 thông qua setPasswordHash()
- Hệ thống tạo tài khoản Member.
- Thông báo đăng ký thành công	- Người dùng mở trang đăng nhập
- Nhập username + mật khẩu
- Hệ thống hash mật khẩu nhập vào,so sánh với passwordHash.
- Nếu trùng khớp thì đăng nhập thành công
- Hệ thống chuyển hướng tùy theo role

	- Người dùng chọn “Quên mật khẩu”.
- Nhập email khôi phục.
- Hệ thống gửi link đặt lại mật khẩu.
- Người dùng mở link → nhập mật khẩu mới.
- Hệ thống cập nhật passwordHash.
	- Người dùng chọn “Thông tin cá nhân”.
- Hệ thống hiển thị form gồm: fullName, email, password.
- Người dùng chỉnh sửa.
- Nếu đổi password → gọi setPasswordHash() để mã hóa SHA-512
- Hệ thống cập nhật vào Database.
- Thông báo thành công.

Exeption flow	- Email đã tồn tại → báo lỗi.
- Username đã tồn tại → báo lỗi.
- Password không đạt chuẩn → báo lỗi.
- Người dùng chọn hủy → Use case kết thúc.
- Người dùng nhập lại thông tin → quay lại bước 3.	- Sai mật khẩu → báo lỗi → quay lại bước 2.
- Tài khoản không tồn tại.
- Nhập thiếu trường.
	- Email không tồn tại.
- Link hết hạn
	- Email sai định dạng.
- Email đã có người khác sử dụng.
- Mật khẩu yếu

Business rule	- Mật khẩu tối thiểu 8 ký tự.
- Email phải theo định dạng hợp lệ		Link reset valid 2 phút.	Email phải duy nhất
Non-functional requirement	- Hash SHA-512 được áp dụng đúng chuẩn 
- Form đăng ký phản hồi trong < 2 giây		Email reset phải được gửi tới trong vòng < 5 giây.
	Thời gian tải form < 3 giây




Use case ID	UC5	UC6	UC7
Use case name	Thêm tài liệu	Xóa tài liệu	Mượn/trả tài liệu
Description	Tạo bản ghi tài liệu mới trong thư viện	Xoá tài liệu khỏi hệ thống	Người dùng gửi yêu cầu mượn tài liệu.
Actor(s)	Librarian	Librarian	Member
Priority	Must have	Must have	Must have
Trigger			
Pre-condition	- Thủ thư đăng nhập.
- ISBN chưa tồn tại.
	Tài liệu không thuộc giao dịch mượn.	- amount > 0
- Member chưa vượt hạn mức mượn

Post-condition	Document mới được lưu	Document bị xoá khỏi DB	Yêu cầu mượn được ghi vào hệ thống
Basic flow	- Thủ thư chọn “Thêm tài liệu”.
- Hệ thống hiển thị form.
- Thủ thư nhập thông tin:
title, author, publisher, isbn, language, pageCount, amount…
-  Hệ thống kiểm tra valid.
- Lưu Document.
- Thông báo thành công.
	- Thủ thư chọn tài liệu.
- Nhấn “Xoá”.
- Hệ thống yêu cầu xác nhận.
- Thủ thư xác nhận.
- Hệ thống xoá.
	- Member chọn tài liệu.
- Nhấn “Mượn tài liệu”.
- Hệ thống kiểm tra amount.
- amount > 0 → tạo yêu cầu mượn.
- Hiển thị thông báo chờ duyệt

Exeption flow	- ISBN đã tồn tại.
- amount < 0.
- pageCount không hợp lệ	Tài liệu đang được mượn → từ chối xoá.	- amount = 0 → báo “Hết tài liệu”.
- Member vượt giới hạn mượn



Business rule	ISBN là duy nhất	Không được xoá tài liệu nếu amount < totalBorrowed.	Mỗi Member tối đa 5 tài liệu cùng lúc

Non-functional requirement	Form phản hồi < 2 giây	Tác vụ xoá < 1 giây	Ghi nhận yêu cầu < 1 giây.




Use case ID	UC8	UC9	UC10
Use case name	Duyệt yêu cầu mượn	Gửi yêu cầu trả tài liệu	Duyệt yêu cầu trả tài liệu
Description	Thủ thư xử lý yêu cầu mượn tài liệu	Chức năng cho phép Member gửi yêu cầu trả lại tài liệu mà họ đang mượn. Yêu cầu này được chuyển đến Librarian để xác nhận.	Chức năng cho phép Librarian xử lý yêu cầu trả tài liệu do Member gửi
Actor(s)	Librarian	Member	Librarian
Priority	Must have	Must have	Must have
Trigger		Member muốn trả một tài liệu đang mượn	Librarian mở danh sách yêu cầu trả để xử lý
Pre-condition	- Có yêu cầu mượn đang chờ duyệt.
- amount > 0
	- Member đã đăng nhập.
- Tài liệu đang được Member mượn (thuộc danh sách “BorrowedDocuments” của tài khoản đó).
- Không có giao dịch trả đang chờ xử lý cho tài liệu đó	- Librarian đã đăng nhập.
- Yêu cầu trả (ReturnRequest) đang ở trạng thái “Pending”.
- Tài liệu hợp lệ và nằm trong lịch sử mượn của Member

Post-condition	- amount--
- Phiếu mượn được tạo.
	- Yêu cầu trả tài liệu được lưu vào hệ thống ở trạng thái “Pending Return Approval”.
- Librarian sẽ xem và xử lý
	- Tài liệu được đánh dấu “Đã trả”. amount trong Document được tăng lên 1.
- Trạng thái yêu cầu trả chuyển sang “Completed”.
- Nếu có phạt → ghi nhận vào hệ thống.
Basic flow	- Thủ thư mở danh sách yêu cầu mượn.
- Kiểm tra amount.
- amount > 0 → duyệt.
- System giảm amount--
- Tạo phiếu mượn.
-Thông báo thành công.
	- Member mở danh sách “Tài liệu đang mượn”.
- Member chọn tài liệu muốn trả.
- Member nhấn nút “Trả tài liệu”.
- Hệ thống hiển thị popup xác nhận.
- Member nhấn “Xác nhận trả”.
- Hệ thống kiểm tra điều kiện:
+ Member thật sự đang mượn tài liệu.
+ Không có yêu cầu trả trước đó.
- Hệ thống tạo bản ghi ReturnRequest với trạng thái “Pending”.
Hệ thống thông báo “Yêu cầu trả đã được gửi đến thủ thư”.
	- Librarian vào mục “Yêu cầu trả tài liệu”.
- Hệ thống hiển thị danh sách yêu cầu đang chờ xử lý.
- Librarian chọn một yêu cầu.
- Hệ thống hiển thị thông tin chi tiết:
+ Member nào mượn
+ Tên tài liệu
+ Ngày mượn
+ Ngày phải trả
+ Số ngày trả trễ (nếu có)
- Librarian kiểm tra tình trạng vật lý của tài liệu.
- Librarian nhấn “Xác nhận trả”.
- Hệ thống thực hiện:
+ Tăng amount++ cho Document
+ Cập nhật transaction “Returned = true”
+Tính phạt nếu trả trễ (nếu có rule)
- Hệ thống thông báo “Trả tài liệu thành công”.
Exeption flow	amount = 0 → từ chối → thông báo	- Member chọn tài liệu không thuộc danh sách đang mượn → báo lỗi “Bạn không mượn tài liệu này”.
- Hệ thống phát hiện đã tồn tại yêu cầu trả trước đó → báo lỗi “Yêu cầu trả đang được xử lý”.
- Hệ thống không thể kết nối Database → báo lỗi hệ thống, yêu cầu thử lại.
	- Yêu cầu trả không còn tồn tại (đã bị xử lý bởi thủ thư khác) → báo “Yêu cầu không hợp lệ”.
- Tài liệu bị hư hỏng → hệ thống yêu cầu tạo Phiếu bồi thường.
- amount đang bị lỗi (âm hoặc null) → blocking error → yêu cầu kiểm tra dữ liệu.
- Lỗi kết nối Database → giao dịch chưa hoàn thành → Librarian phải thao tác lại.
Business rule	Mỗi request chỉ xử lý một lần	- Một tài liệu chỉ có 1 yêu cầu trả tại một thời điểm.
- Member không thể trả tài liệu thay cho người khác.
	- Khi trả trễ hạn → tính tiền phạt theo quy định thư viện.
- Nếu tài liệu bị hỏng → Member phải bồi thường theo giá trị mà thư viện quy định.
- Không được xác nhận trả khi chưa kiểm tra tình trạng tài liệu.
- amount không được âm trong bất kỳ trường hợp nào.
Non-functional requirement	Duyệt yêu cầu < 2 giây	- Việc tạo yêu cầu trả phải hoàn thành trong < 1 giây.
- UI phải cảnh báo rõ ràng để tránh trả nhầm tài liệu.
- Dữ liệu yêu cầu trả phải được lưu an toàn và không bị mất khi server lỗi.
	- Xác nhận trả phải được xử lý trong < 2 giây.
- Dữ liệu giao dịch phải đảm bảo tính toàn vẹn 
- Hệ thống phải log lại mọi hoạt động của Librarian để phục vụ kiểm tra.

