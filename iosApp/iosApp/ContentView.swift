import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    private var lifeCycleHolder:LifeCycleHolder {LifeCycleHolder()}
    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController(lifecycle:lifeCycleHolder.lifecycle)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}
